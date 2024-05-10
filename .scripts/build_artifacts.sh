function install() {
    mvn -f "./$1" clean install
}

echo "Building JAR artifacts"

install "m2m-banking-api"
install "client"
install "infrastructure"
install "account-management"
install "account-query"
install "transaction-processor"
