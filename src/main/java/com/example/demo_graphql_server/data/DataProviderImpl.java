package com.example.demo_graphql_server.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_graphql_server.common.CourierConst;
import com.example.demo_graphql_server.common.PaymentMethodConst;
import com.example.demo_graphql_server.common.PointRateConst;
import com.example.demo_graphql_server.entity.PurchaseInfEntity;
import com.example.demo_graphql_server.model.AdditionalItemInput;
import com.example.demo_graphql_server.model.PurchaseInf;
import com.example.demo_graphql_server.model.Sales;
import com.example.demo_graphql_server.repository.PurchaseInfRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataProviderImpl implements DataProvider {

	@Autowired
	private PurchaseInfRepository purchaseInfRepository;

	private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	@Override
	@Transactional
	public PurchaseInf calcPurchaseInf(String customerId, String price, double priceModifier, String paymentMethod,
			String datetime, AdditionalItemInput additionalItem) throws DataProviderException{

		// 価格の計算
		double priceValue = Double.parseDouble(price);
		double finalPrice = priceValue * priceModifier;
		int point = 0;
		boolean isValid = true;

		// 支払い方法ごとの処理
		switch (paymentMethod) {
		case PaymentMethodConst.CASH:
			point = (int) Math.round(priceValue * PointRateConst.CASH_POINT_RATE);
			isValid = priceValue * 0.9 <= finalPrice && finalPrice <= priceValue;
			break;
		case PaymentMethodConst.CASH_ON_DELIVERY:
			point = (int) Math.round(priceValue * PointRateConst.CASH_ON_DELIVERY_POINT_RATE);
			isValid = priceValue <= finalPrice && finalPrice <= priceValue * 1.02
					&& (CourierConst.YAMATO.equals(additionalItem.getCourier())
							|| CourierConst.SAGAWA.equals(additionalItem.getCourier()));
			break;
		case PaymentMethodConst.VISA:
			point = (int) Math.round(priceValue * PointRateConst.VISA_POINT_RATE);
			isValid = priceValue * 0.95 <= finalPrice && finalPrice <= priceValue;
			break;
		case PaymentMethodConst.MASTERCARD:
			point = (int) Math.round(priceValue * PointRateConst.MASTERCARD_POINT_RATE);
			isValid = priceValue * 0.95 <= finalPrice && finalPrice <= priceValue;
			break;
		case PaymentMethodConst.AMEX:
			point = (int) Math.round(priceValue * PointRateConst.AMEX_POINT_RATE);
			isValid = priceValue * 0.98 <= finalPrice && finalPrice <= priceValue * 1.01;
			break;
		case PaymentMethodConst.JCB:
			point = (int) Math.round(priceValue * PointRateConst.JCB_POINT_RATE);
			isValid = priceValue * 0.95 <= finalPrice && finalPrice <= priceValue;
			break;
		case PaymentMethodConst.LINE_PAY:
		case PaymentMethodConst.PAYPAY:
		case PaymentMethodConst.GRAB_PAY:
			point = (int) Math.round(priceValue * PointRateConst.LINE_PAY_POINT_RATE);
			isValid = priceValue == finalPrice;
			break;
		case PaymentMethodConst.POINTS:
			point = (int) PointRateConst.POINT_RATES;
			isValid = priceValue == finalPrice;
			break;
		case PaymentMethodConst.BANK_TRANSFER:
			point = (int) PointRateConst.BANK_TRANSFER_POINT_RATE;
			isValid = priceValue == finalPrice;
			break;
		case PaymentMethodConst.CHEQUE:
			point = (int) PointRateConst.CHEQUE_POINT_RATE;
			isValid = priceValue * 0.9 <= finalPrice && finalPrice <= priceValue;
			break;
		default:
			isValid = false;
		}

		// 無効な場合はnullを返す
		if (!isValid) {
			throw new DataProviderException("InValid Data");
		}

		// PurchaseInfEntityの作成と保存
		PurchaseInfEntity purchaseInfEntity = createPurchaseInfEntity(customerId, price, priceModifier, finalPrice,
				point, paymentMethod, datetime, additionalItem);
		purchaseInfRepository.save(purchaseInfEntity);

		return new PurchaseInf(finalPrice, point);
	}

	@Override
	@Transactional
	public List<Sales> getSalesInf(String startDateTime, String endDateTime) throws DataProviderException{

		// OffsetDateTimeを使用して文字列をパース
		OffsetDateTime offsetStartDateTime = OffsetDateTime.parse(startDateTime);
		OffsetDateTime offsetEndDateTime = OffsetDateTime.parse(endDateTime);

		// LocalDateTimeに変換
		LocalDateTime localStartDateTime = offsetStartDateTime.toLocalDateTime();
		LocalDateTime localEndDateTime = offsetEndDateTime.toLocalDateTime();
		
		if(localStartDateTime.isAfter(localEndDateTime)) {
			throw new DataProviderException("StartDate must be before EndDate");
		}
		
		// 日付範囲でデータを取得
		List<PurchaseInfEntity> entities = purchaseInfRepository.findByDatetimeBetween(localStartDateTime,
				localEndDateTime);

		// purchaseInfから、売上データのみ取得
		List<Sales> salesList = entities.stream().map(entity -> new Sales(entity.getDatetime().format(formatter),
				entity.getFinalPrice().doubleValue(), entity.getPointsEarned())).collect(Collectors.toList());
		return salesList;
	}

	private PurchaseInfEntity createPurchaseInfEntity(String customerId, String price, double priceModifier,
			double finalPrice, int points, String paymentMethod, String datetime, AdditionalItemInput additionalItem) {

		// PurchaseInfEntityの作成
		PurchaseInfEntity entity = new PurchaseInfEntity();
		entity.setCustomerId(Integer.parseInt(customerId)); // customerIdをIntegerに変換して設定
		entity.setPrice(new BigDecimal(price)); // priceをBigDecimalに変換して設定
		entity.setPriceModifier(BigDecimal.valueOf(priceModifier)); // priceModifierをBigDecimalに変換して設定
		entity.setFinalPrice(BigDecimal.valueOf(finalPrice)); // finalPriceをBigDecimalに変換して設定
		entity.setPointsEarned(points); // ポイントを設定
		entity.setPaymentMethod(paymentMethod); // 支払い方法を設定
		entity.setDatetime(LocalDateTime.parse(datetime, formatter)); // dateTimeをLocalDateTimeに変換して設定
		entity.setCreatedAt(LocalDateTime.now()); // 現在の日時を設定

		// 追加情報がある場合、JSON形式に変換して設定
		if (additionalItem != null) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				String additionalInfoJson = objectMapper.writeValueAsString(additionalItem);
				entity.setAdditionalInfo(additionalInfoJson);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			entity.setAdditionalInfo(null);
		}

		return entity;
	}
}
