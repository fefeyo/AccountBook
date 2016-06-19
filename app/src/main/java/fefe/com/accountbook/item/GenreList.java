package fefe.com.accountbook.item;

/**
 * Created by USER on 2015/12/18.
 */
public class GenreList {
    private static String[] genres = {
            "食費",
            "日用品費",
            "光熱費",
            "交際費",
            "衣服費",
            "交通費",
            "趣味",
            "雑費",
            "生活費"
    };
    private static String[] genre_icons = {
            "fa_cutlery",
            "fa_home",
            "fa_sun_o",
            "fa-users",
            "fa_female",
            "fa_cab",
            "fa_child",
            "fa_shopping_cart",
            "fa_heart"
    };
    public static String getGenre(int genreId){return genres[genreId - 1];}
    public static String getGenreIcon(int genreId){return genre_icons[genreId - 1];}
}
