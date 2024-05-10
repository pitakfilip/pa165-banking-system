function build() {
  cd "$1" || exit
  docker build -t "$1" .
  cd ..
}

echo "Building docker images"

build "client"
build "account-management"
build "account-query"
build "transaction-processor"
