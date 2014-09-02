glassfish embeddedなアプリケーションの模索
==============================================

jetty内蔵のWebアプリケーションのように、glassfishを内蔵したWebアプリケーション作成し、
インストールや起動が手軽な方法を模索したプロジェクト。

## 実行

    $ mvn package
    $ cd embedded-exec/target/dist-package
    $ bin/embedded-main web-project-1.0-SNAPSHOT.war    #引数でwarを指定

- <http://localhost:8080/>  でindex.htmlを表示
- <http://localhost:8080/webresources/customers>  でJAX-RSのJPA検索結果のJSONレスポンス
- <http://localhost:8080/webresources/customers/hello> でJPAを使わないJAX-RSのJSONレスポンス


## 構成

- web-project: Webアプリケーション本体
- embedded-exec: 起動用Javaアプリケーション

### web-project

JAX-RS, JPAを使ったサンプルプログラム。

embeddedであることに依存しておらず、通常のGlassfishサーバーにデプロイすることも可能。
(その場合はJDBCリソースをサーバー側で用意する必要がある）

JPAのPersistenceUnitは、jdbc/TestPoolを参照している。


### embedded-exec

embedded-execは、glassfish-embeddedを起動して、web-projectを起動するアプリケーション。

h2データベースをインメモリで動作させ、初期データを作成している。

glassfishでの接続プール作成(TestPool)、JDBCリソース(jdbc/TestPool)作成を行っている。JDBCリソースはweb-projectから参照される。





