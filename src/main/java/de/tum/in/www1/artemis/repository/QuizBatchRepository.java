package de.tum.in.www1.artemis.repository;

import static de.tum.in.www1.artemis.config.Constants.PROFILE_CORE;

import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.tum.in.www1.artemis.domain.quiz.QuizBatch;
import de.tum.in.www1.artemis.domain.quiz.QuizExercise;
import de.tum.in.www1.artemis.repository.base.ArtemisJpaRepository;

/**
 * Spring Data JPA repository for the QuizBatch entity.
 */
@Profile(PROFILE_CORE)
@Repository
public interface QuizBatchRepository extends ArtemisJpaRepository<QuizBatch, Long> {

    Set<QuizBatch> findAllByQuizExercise(QuizExercise quizExercise);

    Set<QuizBatch> findAllByQuizExerciseAndCreator(QuizExercise quizExercise, Long creator);

    Optional<QuizBatch> findByQuizExerciseAndPassword(QuizExercise quizExercise, String password);

    Optional<QuizBatch> findFirstByQuizExercise(QuizExercise quizExercise);

    /**
     * Retrieve QuizBatch for given quiz exercise and studentLogin
     *
     * @param quizExercise the quiz exercise for which QuizBatch is to be retrieved
     * @param studentLogin the login of the student for which QuizBatch is to be retrieved
     * @return QuizBatch for given quiz exercise and studentLogin
     */
    @Query("""
            SELECT quizBatch
            FROM QuizBatch quizBatch
                JOIN QuizSubmission submission ON quizBatch.id = submission.quizBatch
                JOIN TREAT(submission.participation AS StudentParticipation) participation
            WHERE participation.exercise = :quizExercise
                AND participation.student.login = :studentLogin
            """)
    Optional<QuizBatch> findByQuizExerciseAndStudentLogin(@Param("quizExercise") QuizExercise quizExercise, @Param("studentLogin") String studentLogin);
}
