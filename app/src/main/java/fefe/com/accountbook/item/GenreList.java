package fefe.com.accountbook.item;

/**
 * Created by USER on 2015/12/18.
 */
public class GenreList {
    private static String[] genres = {
            "食費",
            "住居費",
            "光熱費",
            "交際費",
            "衣服費",
            "交通費",
            "趣味",
            "雑費"
    };
    public static String getGenre(int genreId){
        return genres[genreId];
    }
}
