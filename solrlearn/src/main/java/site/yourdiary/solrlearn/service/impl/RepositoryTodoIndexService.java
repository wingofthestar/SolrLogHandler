package site.yourdiary.solrlearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yourdiary.solrlearn.document.TodoDocument;
import site.yourdiary.solrlearn.repository.TodoDocumentRepository;
import site.yourdiary.solrlearn.service.TodoIndexService;

@Service
public class RepositoryTodoIndexService implements TodoIndexService {

    @Autowired
    private TodoDocumentRepository todoDocumentRepository;

    @Override
    public void addToIndex(TodoDocument todoDocument) {
        todoDocumentRepository.save(todoDocument);
    }

    @Override
    public void deleteFromIndex(String id) {
        todoDocumentRepository.delete(id);
    }
}
