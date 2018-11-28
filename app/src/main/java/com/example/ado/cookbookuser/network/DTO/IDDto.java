package com.example.ado.cookbookuser.network.DTO;

import java.util.List;

public class IDDto {

    /**
     * resultcode : 200
     * reason : Success
     * result : {"data":[{"id":"1001","title":"糖醋小排","tags":"浙菜;热菜;儿童;酸甜;快手菜","imtro":"糖醋小排，我估计爱吃的人太多了，要想做好这道菜，关键就是调料汁的配置，老抽不能放的太多，那样颜色太重， 不好看，调料汁调好后，最好尝一下，每个人的口味都会不同的，可以适当微调一下哈！","ingredients":"肋排,500g","burden":"葱,适量;白芝麻,适量;盐,3g;生粉,45g;料酒,30ml;鸡蛋,1个;葱,1小段;姜,3片;老抽,7ml;醋,30ml;白糖,20g;番茄酱,15ml;生抽,15ml;生粉,7g;姜,适量","albums":["http://img.juhe.cn/cookbook/t/1/1001_253951.jpg"],"steps":[{"img":"http://img.juhe.cn/cookbook/s/10/1001_40ec58177e146191.jpg","step":"1.排骨剁小块，用清水反复清洗，去掉血水"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_034906d012e61fcc.jpg","step":"2.排骨放入容器中，放入腌料，搅拌均匀，腌制5分钟"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_b04cddaea2a1a604.jpg","step":"3.锅中放适量油，烧至5成热，倒入排骨，炸至冒青烟时捞出，关火，等油温降至五成热时，开火，再次放入排骨，中火炸至焦黄、熟透捞出"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_56b92264df500f01.jpg","step":"4.锅中留少许底油，放入葱花、姜片爆香"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_d78c57536a08dc4b.jpg","step":"5.放入适量炸好的排骨，倒入调料汁，煮至汤汁浓稠时，关火，撒入葱花、白芝麻点缀即可"}]}],"totalNum":1,"pn":0,"rn":1}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * data : [{"id":"1001","title":"糖醋小排","tags":"浙菜;热菜;儿童;酸甜;快手菜","imtro":"糖醋小排，我估计爱吃的人太多了，要想做好这道菜，关键就是调料汁的配置，老抽不能放的太多，那样颜色太重， 不好看，调料汁调好后，最好尝一下，每个人的口味都会不同的，可以适当微调一下哈！","ingredients":"肋排,500g","burden":"葱,适量;白芝麻,适量;盐,3g;生粉,45g;料酒,30ml;鸡蛋,1个;葱,1小段;姜,3片;老抽,7ml;醋,30ml;白糖,20g;番茄酱,15ml;生抽,15ml;生粉,7g;姜,适量","albums":["http://img.juhe.cn/cookbook/t/1/1001_253951.jpg"],"steps":[{"img":"http://img.juhe.cn/cookbook/s/10/1001_40ec58177e146191.jpg","step":"1.排骨剁小块，用清水反复清洗，去掉血水"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_034906d012e61fcc.jpg","step":"2.排骨放入容器中，放入腌料，搅拌均匀，腌制5分钟"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_b04cddaea2a1a604.jpg","step":"3.锅中放适量油，烧至5成热，倒入排骨，炸至冒青烟时捞出，关火，等油温降至五成热时，开火，再次放入排骨，中火炸至焦黄、熟透捞出"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_56b92264df500f01.jpg","step":"4.锅中留少许底油，放入葱花、姜片爆香"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_d78c57536a08dc4b.jpg","step":"5.放入适量炸好的排骨，倒入调料汁，煮至汤汁浓稠时，关火，撒入葱花、白芝麻点缀即可"}]}]
         * totalNum : 1
         * pn : 0
         * rn : 1
         */

        private int totalNum;
        private int pn;
        private int rn;
        private List<DataBean> data;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getRn() {
            return rn;
        }

        public void setRn(int rn) {
            this.rn = rn;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1001
             * title : 糖醋小排
             * tags : 浙菜;热菜;儿童;酸甜;快手菜
             * imtro : 糖醋小排，我估计爱吃的人太多了，要想做好这道菜，关键就是调料汁的配置，老抽不能放的太多，那样颜色太重， 不好看，调料汁调好后，最好尝一下，每个人的口味都会不同的，可以适当微调一下哈！
             * ingredients : 肋排,500g
             * burden : 葱,适量;白芝麻,适量;盐,3g;生粉,45g;料酒,30ml;鸡蛋,1个;葱,1小段;姜,3片;老抽,7ml;醋,30ml;白糖,20g;番茄酱,15ml;生抽,15ml;生粉,7g;姜,适量
             * albums : ["http://img.juhe.cn/cookbook/t/1/1001_253951.jpg"]
             * steps : [{"img":"http://img.juhe.cn/cookbook/s/10/1001_40ec58177e146191.jpg","step":"1.排骨剁小块，用清水反复清洗，去掉血水"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_034906d012e61fcc.jpg","step":"2.排骨放入容器中，放入腌料，搅拌均匀，腌制5分钟"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_b04cddaea2a1a604.jpg","step":"3.锅中放适量油，烧至5成热，倒入排骨，炸至冒青烟时捞出，关火，等油温降至五成热时，开火，再次放入排骨，中火炸至焦黄、熟透捞出"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_56b92264df500f01.jpg","step":"4.锅中留少许底油，放入葱花、姜片爆香"},{"img":"http://img.juhe.cn/cookbook/s/10/1001_d78c57536a08dc4b.jpg","step":"5.放入适量炸好的排骨，倒入调料汁，煮至汤汁浓稠时，关火，撒入葱花、白芝麻点缀即可"}]
             */

            private String id;
            private String title;
            private String tags;
            private String imtro;
            private String ingredients;
            private String burden;
            private List<String> albums;
            private List<StepsBean> steps;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getImtro() {
                return imtro;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getBurden() {
                return burden;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public List<String> getAlbums() {
                return albums;
            }

            public void setAlbums(List<String> albums) {
                this.albums = albums;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public static class StepsBean {
                /**
                 * img : http://img.juhe.cn/cookbook/s/10/1001_40ec58177e146191.jpg
                 * step : 1.排骨剁小块，用清水反复清洗，去掉血水
                 */

                private String img;
                private String step;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }
            }
        }
    }
}
