from locust import HttpUser, between, task
import uuid


class BankingUser(HttpUser):
    host = "http://localhost:8080"
    wait_time = between(1, 3)

    @task(1)
    def customer_showcase(self):
        # create user
        user_data = {
            "email": "test@example.com",
            "password": "password123",
            "firstName": "John",
            "lastName": "Doe",
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
            self.account_id = response.json()["id"]
        else:
            raise Exception("Failed to create account")

        # create balance for the account
        balance_data = {
            "id": self.account_id
        }
        response = self.client.post("http://localhost:8081/balance/new", params=balance_data)
        if response.status_code != 201:
            raise Exception("Failed to create balance")

        # deposit to account balance
        deposit_data = {
            "id": self.account_id,
            "amount": 500,
            "processId": str(uuid.uuid4()),
            "type": "DEPOSIT"
        }
        response = self.client.post("http://localhost:8081/balance/add", params=deposit_data)
        if response.status_code != 200:
            raise Exception("Failed to deposit money")

        # TODO: transfer money

    @task(2)
    def employee_showcase(self):
        pass
        # TODO: transaction statistics

        # TODO: revert transaction