function install() {
    mvn -f "./$1" clean install -Dmaven.test.skip=true
}

echo "Building JAR artifacts"

install "infrastructure"
install "m2m-banking-api"
install "account-management"
install "account-query"
install "transaction-processor"
