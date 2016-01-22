# mapdb-test

This package shows an apparent performance deficiency in MapDB. When transactions are disabled on a tempfiledb, I have observed that the performance degrades by an order of magnitude.

This is a very confusing situation. In other respects MapDB performs superbly. One would expect disabling transactions to speed up a database like this. Also, the source code reveals a warning message:

    HTreeMap Expiration should not be used with transaction enabled. It can lead to data corruption, commit might happen while background thread works, and only part of expiration data will be commited.

# Running it

    mvn clean compile assembly:single
    java -jar target/testbed-1.0-SNAPSHOT-jar-with-dependencies.jar

# Sample output

    Took: 3610 with useTransactions: false and useExpire: false
    Took: 5077 with useTransactions: false and useExpire: true
    Took: 152 with useTransactions: true and useExpire: false
    Took: 151 with useTransactions: true and useExpire: true
