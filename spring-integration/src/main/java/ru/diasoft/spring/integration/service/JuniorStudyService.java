package ru.diasoft.spring.integration.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.integration.domain.Grade;
import ru.diasoft.spring.integration.domain.Programmer;

import java.util.concurrent.TimeUnit;

import static ru.diasoft.spring.integration.utils.Constants.BEAN_NAME_JUNIOR_STUDY_SERVICE;
import static ru.diasoft.spring.integration.utils.Constants.STUDY_DURATION_SECONDS;

@Log4j2
@Service(BEAN_NAME_JUNIOR_STUDY_SERVICE)
public class JuniorStudyService implements StudyService {

    @Override
    public Programmer study(Programmer programmer) throws Exception {
        log.info(programmerStartStudy(programmer));
        TimeUnit.SECONDS.sleep(STUDY_DURATION_SECONDS);
        final int chance = RandomUtils.nextInt(0, 10);
        if (chance > 3) {
            log.info(programmerEndStudy(programmer));
            programmer.setGrade(targetGrade());
        }
        return programmer;
    }

    @Override
    public Grade targetGrade() {
        return Grade.JUNIOR;
    }

    @Override
    public Grade lastGrade() {
        return Grade.NO_GRADE;
    }
}
