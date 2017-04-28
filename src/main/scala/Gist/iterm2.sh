#!/usr/bin/env bash

ls -alt # 按修改时间排序  ls -alrt 
ls -alc # 按创建时间排序
ls -alu # 按访问时间排序

tail -f logs/adapter.log
tail -f adapter.log | grep ERROR
#or
less adapter.log | grep -n ERROR
less logs/* | grep -n SOAPAction >1234.log

#socgen1:
ssh -t -p22 -lanonymous -i/Users/zhanghongwei/.ssh/tesobe deploy@10.0.0.37
sudo -iu deploy
./kafka/kafka_monitor.sh
tail -f logs/adapter.log
tail -f logs/jettylogs/2017_03_16.stderrout.log


#Socgen2:
ssh -t -p22 -lanonymous -i/Users/zhanghongwei/.ssh/tesobe deploy@10.0.0.128
ssh -L 0.0.0.0:46051:172.16.0.4:46051 localhost
curl -X GET http://10.0.0.128:46051/getAccountDetail?wsdl

#apisanbox :
ssh -t -p22 -lanonymous -i/Users/zhanghongwei/.ssh/tesobe deploy@10.0.0.51

#siop:
ssh -t -p443 -lsiop -i/Users/zhanghongwei/.ssh/tesobe totemvmvpn.cloudapp.net

#k1:
ssh -t -p22 -lanonymous -i/Users/zhanghongwei/.ssh/tesobe deploy@10.0.0.141
cd /home/deploy && ./kafka_monitor.sh
#See /etc/jetty8 for configuration, /var/log/jetty8 for logfiles
tail -f /var/log/jetty8/2017_03_29.stderrout.log
psql -U k1_api -h localhost -W k1_api    pass: che0PuPh7ieJ


#kafka:
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &

#psql:
sudo -iu postgres
psql
\l
\c socgen2_obp_api_kafka_sandbox

SELECT * FROM mappedbadloginattempt 
ORDER BY id
LIMIT 20 ;

SELECT * FROM mappedaccountholder 
ORDER BY id
LIMIT 20 ;


UPDATE "mappedbadloginattempt" 
SET "mbadattemptssincelastsuccessorreset"='0' 
WHERE "id"=3


#Standalone-mode-comands:
#5.1 get banks
 curl -X GET http://localhost:4567/obp/demo/banks

#5.2 get bank acount by UserID
 curl -X GET http://localhost:4567/obp/demo/bank/0100/accounts\?u\=1000203899

#5.3 get bank account by accountID
curl -X GET http://localhost:4567/obp/demo/bank/0100/account/0796d146-e39c-36a1-85cd-ef74f5d8227d?u=1000203899

#5.4 get Transaction by UserID
curl -X GET http://localhost:4567/obp/demo/bank/0100/account/0796d146-e39c-36a1-85cd-ef74f5d8227d/transactions?u=1000203904

#5.5 get Transactions by TransactionId
curl -X GET http://localhost:4567/obp/demo/bank/0100/account/0796d146-e39c-36a1-85cd-ef74f5d8227d/transaction/c163349c-9d00-31d9-a70e-04eeae80fd24?u=1000203899

#5.6 Create Transactions
curl -X GET http://localhost:4567/obp/demo/save/:debtor/:daccount/:creditor/:caccount/:sum/:fee
curl -X GET http://localhost:4567/obp/demo/save/1000203899/bb912420-484d-38c2-8c5b-d9772dd5bfbc/1000203899/0796d146-e39c-36a1-85cd-ef74f5d8227d/42.24/1

#5.7 SocGen’s builtin REST server can now fetch files from sftp
curl -X GET http://localhost:4567/obp/demo/fetch

#6 Check OBP-API Version
curl http://localhost:8080/obp/v2.1.0
curl http://localhost:8083/obp/v2.1.0 for Socgen server

