package ca.dal.bartertrader.utils;

public class CategoryHelper {
    public String CategoryToString(CategoryEnum category)
    {
        switch (category)
        {
            case ELECTRONICS:
                return "Electronics";
            case CLOTHING:
                return "Clothing";
            case FURNITURE:
                return "Furniture";
            case BOOKS:
                return "Books";
            case SPORTING_GOODS:
                return "Sporting Goods";
            case TOOLS:
                return "Tools";
            case HOBBIES_AND_CRAFTS:
                return "Hobbies & Crafts";
            case MISC:
                return "Misc";
            default:
                return "Unknown Category";
        }
    }
}