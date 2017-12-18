package site.yourdiary.solrlearn.service;

import site.yourdiary.solrlearn.document.TodoDocument;

public interface TodoIndexService {

    public void addToIndex(TodoDocument todoDocument);

    public void deleteFromIndex(String id);
}
