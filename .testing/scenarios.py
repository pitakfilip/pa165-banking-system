from locust import HttpUser, between, task, events
import uuid

# Uncomment in case of running Locust via CLI instead of docker-compose
# managementHost = "localhost"
# queryHost = "localhost"
# processHost = "localhost"
managementHost = "account-management"
queryHost = "account-query"
processHost = "transaction-processor"

@events.init_command_line_parser.add_listener
def _(parser):
    parser.add_argument(
        "--customer-token",
        type=str,
        default="",
        help="Customer OAuth2 Token",
    )
    parser.add_argument(
        "--employee-token",
        type=str,
        default="",
        help="Employee OAuth2 Token",
    )

@events.test_start.add_listener
def _(environment, **kw):
    print(f"Custom argument supplied: {environment.parsed_options.customer_token}")
    print(f"Custom argument supplied: {environment.parsed_options.employee_token}")
    
class BankingUser(HttpUser):
    
    host = f"http://{managementHost}:8080"
    customer_token = ""
    employee_token = ""
    wait_time = between(1, 3)

    def on_start(self):
        self.customer_token = self.environment.parsed_options.customer_token
        self.employee_token = self.environment.parsed_options.employee_token
        print(f"CUSTOMER TOKEN: {self.customer_token}")
        print(f"EMPLOYEE TOKEN: {self.employee_token}")
    @task
    def customer_showcase(self):
        # create user
        user_data = {
            "email": "t.anderson@metacortex.org",
            "password": "password123",
            "firstName": "Thomas",
            "lastName": "Anderson",
            "userType": "REGULAR"
        }
        response = self.client.post("/user", json=user_data)
        if response.status_code == 201:
            self.user_id = response.json()["id"]
        else:
            raise Exception("Failed to create user")

        # create account1
        account_data = {
            "userId": self.user_id,
            "type": "CREDIT",
            "maxSpendingLimit": 1000,
            "currency": "USD"
        }
        response = self.client.post("/account", json=account_data)
        if response.status_code == 201:
            self.account1 = response.json()
        else:
            raise Exception("Failed to create account1")

        # create account2
        account_data = {
            "userId": self.user_id,
            "type": "SAVING",
            "maxSpendingLimit": 1000,
            "currency": "USD"
        }
        response = self.client.post("/account", json=account_data)
        if response.status_code == 201:
            self.account2 = response.json()
        else:
            raise Exception("Failed to create account2")

        # deposit to account1 balance
        deposit_data = {
            "id": self.account1["number"],
            "amount": 500,
            "processId": str(uuid.uuid4()),
            "type": "DEPOSIT"
        }

        response = self.client.post(f"http://{queryHost}:8081/balance/add", params=deposit_data)
        if response.status_code != 200:
            raise Exception("Failed to deposit money")

        # transfer money
        payment_data = {
            "source": {
                "accountNumber": self.account1["number"]
            },
            "target": {
                "accountNumber": self.account2["number"]
            },
            "type": "TRANSFER",
            "amount": {
                "amount": 100,
                "currency": "USD"
            },
            "detail": "Transfer to savings account"
        }
        response = self.client.put(f"http://{processHost}:8082/transaction/v1/process", json=payment_data)
        if response.status_code == 200:
            transaction_id = response.json()["identifier"]
        else:
            raise Exception("Failed to send money from :"+str(self.account1["number"])+str(response.content))

        # create employee
        employee_data = {
            "email": "smith@matrix.com",
            "password": "MrAnderson",
            "firstName": "Agent",
            "lastName": "Smith",
            "userType": "EMPLOYEE"
        }
        response = self.client.post("/user", json=employee_data)
        if response.status_code == 201:
            self.employee_id = response.json()["id"]
        else:
            raise Exception("Failed to create employee")

        # revert transaction
        revert_data = {
            "x-process-uuid": transaction_id
        }
        response = self.client.post(f"http://{processHost}:8082/transaction/v1/revert", headers=revert_data)
        if response.status_code != 200:
            raise Exception("Failed to revert transaction: "+str(transaction_id))

        # account report
        report_data = {
            "id": self.account1["number"],
            "beginning": "2024-01-01",
            "end": "2024-12-31"
        }
        response = self.client.get(f"http://{queryHost}:8081/balance/account/report", params=report_data)
        if response.status_code != 200:
            raise Exception("Failed to get account report: "+self.account1["number"]+str(response.content))
