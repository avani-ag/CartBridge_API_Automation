package RequestsPOJO;
import java.util.List;

public class AddCartRequestPOJO {
        private int userId;
        private List<ProductsPOJO> products;


        // Getters & Setters
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<ProductsPOJO> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsPOJO> products) {
            this.products = products;
        }
    }


