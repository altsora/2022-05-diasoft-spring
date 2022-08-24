package ru.diasoft.spring.integration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.integration.domain.Programmer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Log4j2
@Service
@RequiredArgsConstructor
public class SuperCorporationImpl implements SuperCorporation {
    private static final String[] NAMES = {"Alex", "Bob", "Jack", "Anna", "Den", "Smith"};
    private static final int MAX_PROGRAMMERS = 50;
    private static final int MIN_PROGRAMMERS = 5;
    private final School school;

    @Override
    public void startMassStudy() throws Exception {
        final ForkJoinPool pool = ForkJoinPool.commonPool();

        pool.execute(() -> {
            log.info("Подготовка к обучению");
            final List<Programmer> programmers = recruitmentOfProgrammers();

            log.info("Учёба начинается. Количество программистов на старте: {}", programmers.size());
            school.massStudy(programmers);
        });
        pool.shutdown();
    }

    private List<Programmer> recruitmentOfProgrammers() {
        final int count = RandomUtils.nextInt(MIN_PROGRAMMERS, MAX_PROGRAMMERS);
        final List<Programmer> programmers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            programmers.add(new Programmer(NAMES[RandomUtils.nextInt(0, NAMES.length)]));
        }
        return programmers;
    }
}
