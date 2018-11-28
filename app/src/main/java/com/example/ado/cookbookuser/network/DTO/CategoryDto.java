package com.example.ado.cookbookuser.network.DTO;

import java.util.List;

public class CategoryDto {

    /**
     * resultcode : 200
     * reason : Success
     * result : [{"parentId":"10001","name":"菜式菜品","list":[{"id":"1","name":"家常菜","parentId":"10001"},{"id":"2","name":"快手菜","parentId":"10001"},{"id":"3","name":"创意菜","parentId":"10001"},{"id":"4","name":"素菜","parentId":"10001"},{"id":"5","name":"凉菜","parentId":"10001"},{"id":"6","name":"烘焙","parentId":"10001"},{"id":"7","name":"面食","parentId":"10001"},{"id":"8","name":"汤","parentId":"10001"},{"id":"9","name":"自制调味料","parentId":"10001"}]}]
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * parentId : 10001
         * name : 菜式菜品
         * list : [{"id":"1","name":"家常菜","parentId":"10001"},{"id":"2","name":"快手菜","parentId":"10001"},{"id":"3","name":"创意菜","parentId":"10001"},{"id":"4","name":"素菜","parentId":"10001"},{"id":"5","name":"凉菜","parentId":"10001"},{"id":"6","name":"烘焙","parentId":"10001"},{"id":"7","name":"面食","parentId":"10001"},{"id":"8","name":"汤","parentId":"10001"},{"id":"9","name":"自制调味料","parentId":"10001"}]
         */

        private String parentId;
        private String name;
        private List<ListBean> list;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * name : 家常菜
             * parentId : 10001
             */

            private String id;
            private String name;
            private String parentId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }
    }
}
