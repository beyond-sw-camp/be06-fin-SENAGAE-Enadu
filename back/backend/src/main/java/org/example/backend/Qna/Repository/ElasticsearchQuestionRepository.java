package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Doc.QnaBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticsearchQuestionRepository extends ElasticsearchRepository<QnaBoard, Long> {
}
