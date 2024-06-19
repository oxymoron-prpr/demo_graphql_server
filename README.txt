demo_graphql_server 
これは、ポイントシステムを提供し、複数の決済方法をサポートするPOS統合型のeコマースプラットフォームのテストです。 

必須要件 
- Java 17 
- PostgreSQL 
- Gradle 

セットアップ 
1. リポジトリをクローン 
- コマンドラインを使用する場合： 
git clone https://github.com/yourusername/pos-ecommerce-platform.git cd pos-ecommerce-platform 
- STSを使用する場合： 
- STSを起動し、「File」 > 「Import」 > 「Git」 > 「Projects from Git」を選択します。 
- 「Clone URI」を選択し、リポジトリのURIを入力してクローンを実行します。 

2. データベースを設定 - `application.properties` ファイルにPostgreSQLデータベースの詳細が含まれていることを確認してください。 

3. データベースを作成 データベースの設定手順については、DATABASE_SETUP.txt を参照してください。 

4. Gradleプロジェクトのリフレッシュ 
- コマンドラインを使用する場合： 
gradlew clean gradlew --refresh-dependencies 
- STSを使用する場合： 
- プロジェクトを右クリックし、「Gradle」 > 「Refresh Gradle Project」を選択します。 

5. アプリケーションを実行 
- コマンドラインを使用する場合： gradlew bootRun 
- STSを使用する場合： - プロジェクトを右クリックし、「Gradle Tasks」 > 「bootRun」を選択します。 

6. GraphiQLエンドポイントにアクセス 
- ブラウザを開き、http://localhost:8080/graphiql にアクセスします。 
- GraphiQLを使用してエンドポイントをテストしてください。 クエリの作成アプリケーションの動かし方に関する詳細な手順については、
APP_RUNNING.txt を参照してください。