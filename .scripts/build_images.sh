echo "Building JAR artifacts"

./build_artifacts.sh > /dev/null 2>&1

function build() {
  cd "$1" || exit
  docker build -t "$1" .
  cd ..
}

echo "Building docker images"

build "account-management"
build "account-query"
build "transaction-processor"
