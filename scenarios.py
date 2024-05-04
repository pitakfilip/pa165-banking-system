from locust import HttpUser, between, task
import uuid


class BankingUser(HttpUser):
    host = "http://localhost:8080"
    wait_time = between(1, 3)

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

        # create account
        account_data = {
            "userId": self.user_id,
            "type": "SAVING",
            "maxSpendingLimit": 1000,
            "currency": "USD"
        }
        response = self.client.post("/account", json=account_data)
        if response.status_code == 201:
            self.account = response.json()
        else:
            raise Exception("Failed to create account")

        # create balance for the account
        balance_data = {
            "id": self.account["id"]
        }
        response = self.client.post("http://localhost:8081/balance/new", params=balance_data)
        if response.status_code != 201:
            raise Exception("Failed to create balance")

        # deposit to account balance
        deposit_process_id = str(uuid.uuid4())
        deposit_data = {
            "id": self.account["id"],
            "amount": 500,
            "processId": deposit_process_id,
            "type": "DEPOSIT"
        }
        response = self.client.post("http://localhost:8081/balance/add", params=deposit_data)
        if response.status_code != 200:
            raise Exception("Failed to deposit money")

        """
        # transfer money
        payment_data = {
            "source": {"accountNumber": self.account["accountNumber"]},
            "target": {"accountNumber": self.account["accountNumber"]},
            "type": "TRANSFER",
            "amount": {"amount": 100, "currency": "USD"},
            "detail": "Payment for service"
        }
        response = self.client.put("http://localhost:8082/transaction/v1/process", json=payment_data)
        if response.status_code != 201:
            raise Exception("Failed to send money")
        """

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
            "x-process-uuid": deposit_process_id
        }
        response = self.client.post("http://localhost:8082/transaction/v1/revert", params=revert_data)
        if response.status_code != 200:
            raise Exception("Failed to revert transaction")

        # account report
        report_data = {
            "id": self.account["id"],
            "beginning": "2024-01-01",
            "end": "2024-12-31"
        }
        response = self.client.post("http://localhost:8081/balance/account/report", params=report_data)
        if response.status_code != 200:
            raise Exception("Failed to get account report")
