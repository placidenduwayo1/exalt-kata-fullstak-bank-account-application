package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Operation {
    private String operationId;
    private String type;
    private double mount;
    private String createdAt;

    private Operation(OperationBuilder builder){
        this.operationId = builder.operationId;
        this.type = builder.type;
        this.mount = builder.mount;
        this.createdAt = builder.createdAt;
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

    public static class OperationBuilder {
        private String operationId;
        private String type;
        private double mount;
        private String createdAt;

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
        public Operation build(){
            return new Operation(this);
        }
    }

    @Override
    public String toString() {
        return "Operation [" +
                "operationId='" + operationId + '\'' +
                ", type='" + type + '\'' +
                ", mount=" + mount +
                ", createdAt='" + createdAt + '\'' +
                ']';
    }
}
