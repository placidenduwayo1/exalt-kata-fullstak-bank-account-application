package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Operation {
    private String operationId;
    private String type;
    private double mount;
    private String createdAt;
    private String accountId;
    private Account account;

    private Operation(OperationBuilder builder) {
        this.operationId = builder.operationId;
        this.type = builder.type;
        this.mount = builder.mount;
        this.createdAt = builder.createdAt;
        this.accountId = builder.accountId;
        this.account = builder.account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static class OperationBuilder {
        private String operationId;
        private String type;
        private double mount;
        private String createdAt;
        private String accountId;
        private Account account;

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

        public OperationBuilder account(Account account) {
            this.account = account;
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
                ", account=" + account +
                ']';
    }
}
