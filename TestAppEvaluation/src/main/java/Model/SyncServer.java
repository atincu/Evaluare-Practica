package Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by atincu on 5/22/2014.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SyncServer {

    private int noEntries;
    private List<ContentSyncServer> content;

    public int getNoEntries() {
        return noEntries;
    }

    public void setNoEntries(int noEntries) {
        this.noEntries = noEntries;
    }

    public List<ContentSyncServer> getContent() {
        return content;
    }

    public void setContent(List<ContentSyncServer> content) {
        this.content = content;
    }
}
