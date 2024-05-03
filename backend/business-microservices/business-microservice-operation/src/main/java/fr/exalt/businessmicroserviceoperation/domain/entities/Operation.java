package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Operation {
    private String operationId;
    private String type;
    private double mount;
    private String createdAt;
    private String accountId;
    private BankAccount bankAccount;

    private Operation(OperationBuilder builder) {
        this.operationId = builder.operationId;
        this.type = builder.type;
        this.mount = builder.mount;
        this.createdAt = builder.createdAt;
        this.accountId = builder.accountId;
        this.bankAccount = builder.bankAccount;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static class OperationBuilder {
        private String operationId;
        private String type;
        private double mount;
        private String createdAt;
        private String accountId;
        private BankAccount bankAccount;

        public OperationBuilder operationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public OperationBuilder type(String type) {
            this.type = type;
            return this;
        }

        public OperationBuilder mount(double mount) {
            this.mount = mount;
            return this;
        }

        public OperationBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OperationBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public OperationBuilder account(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public Operation build() {
            return new Operation(this);
        }
    }

    @Override
    public String toString() {
        return "Operation [" +
                "operation id='" + operationId + '\'' +
                ", type='" + type + '\'' +
                ", mount=" + mount +
                ", created at='" + createdAt + '\'' +
                ", account id='" + accountId + '\'' +
                ", account=" + bankAccount +
                ']';
    }
}
