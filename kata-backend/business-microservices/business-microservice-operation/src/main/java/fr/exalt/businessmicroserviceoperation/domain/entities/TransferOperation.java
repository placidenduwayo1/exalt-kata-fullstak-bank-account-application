package fr.exalt.businessmicroserviceoperation.domain.entities;

public class TransferOperation {
    private String transferId;
    private String origin;
    private BankAccount accountOrigin;
    private String destination;
    private BankAccount accountDestination;
    private double mount;
    private String createdAt;

    private TransferOperation(TransferBuilder builder){
        this.transferId = builder.transferId;
        this.origin = builder.origin;
        this.accountOrigin = builder.accountOrigin;
        this.accountDestination = builder.accountDestination;
        this.destination = builder.destination;
        this.mount = builder.mount;
        this.createdAt = builder.createdAt;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransferId() {
        return transferId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getMount() {
        return mount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public BankAccount getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(BankAccount accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public BankAccount getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(BankAccount accountDestination) {
        this.accountDestination = accountDestination;
    }

    public static class TransferBuilder {
        private String transferId;
        private String origin;
        private BankAccount accountOrigin;
        private String destination;
        private BankAccount accountDestination;
        private double mount;
        private String createdAt;

        public TransferBuilder transferId(String transferId) {
            this.transferId = transferId;
            return this;
        }

        public TransferBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public TransferBuilder accountOrigin(BankAccount accountOrigin) {
            this.accountOrigin = accountOrigin;
            return this;
        }

        public TransferBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }
        public TransferBuilder accountDestination(BankAccount accountDestination) {
            this.accountDestination = accountDestination;
            return this;
        }

        public TransferBuilder mount(double mount) {
            this.mount = mount;
            return this;
        }

        public TransferBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TransferOperation build(){
            return new TransferOperation(this);
        }
    }
}
