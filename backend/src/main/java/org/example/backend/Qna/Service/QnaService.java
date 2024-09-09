package org.example.backend.Qna.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Entity.req.CreateQuestionReq;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    public void saveQuestion(CreateQuestionReq req) {
        Optional<Category> category = categoryRepository.findById(req.getCategoryId());

        if (category.isPresent()) {
            QnaBoard qnaBoard = QnaBoard.builder()
                    .title(req.getTitle())
                    .content(req.getContent())
                    .category(category.get())
                    .build();

            qnaBoard.createdAt();
            questionRepository.save(qnaBoard);
        }
        else {
            throw new InvalidQnaException(BaseResponseStatus.INVALID_CATEGORY_DATA);
        }
    }
}
