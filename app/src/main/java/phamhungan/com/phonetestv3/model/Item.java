package phamhungan.com.phonetestv3.model;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class Item {
    private String itemName;
    private int idImage;

    public Item(int idImage, String itemName) {
        this.itemName = itemName;
        this.idImage = idImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
