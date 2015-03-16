package urdu4android.onairm.com.urdu4android.module;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NovelData {

    @SerializedName("statusCode")
    private int statusCode;

	@SerializedName("page")
	private int page;

    @SerializedName("count")
    private int count;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Novel> novels = new ArrayList<Novel>();



    public NovelData(){
    }
    public NovelData(int statusCode, int page, int pageSize, int count, String message,
                     List<Novel> novels) {
		super();
        this.statusCode = statusCode;
		this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.message = message;
		this.novels = novels;
	}

	public int getPage() {
		return page;
	}

    public int getCount() {
        return count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<Novel> getNovels() {
        return novels;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
