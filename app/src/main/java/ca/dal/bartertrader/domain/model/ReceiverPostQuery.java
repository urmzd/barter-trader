package ca.dal.bartertrader.domain.model;

public class ReceiverPostQuery {

    private final Boolean ascending;
    private final String field;
    private final String query;

    public ReceiverPostQuery(Boolean ascending, String field, String query) {
        this.ascending = ascending;
        this.field = field;
        this.query = query;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public String getField() {
        return field;
    }

    public String getQuery() {
        return query;
    }
}
