package site.yourdiary.solrdemo.respository;

import org.springframework.data.solr.repository.SolrRepository;
import site.yourdiary.solrdemo.entity.LoginInfo;

public interface LoginInfoResporitory extends SolrRepository<LoginInfo, String> {

    LoginInfo findById(String uuid);
}
