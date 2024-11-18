package com.ssafy.fittapet.backend.domain.repository.diet;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.diet.QDietResponse;
import com.ssafy.fittapet.backend.domain.entity.QDiet;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DietCustomRepositoryImpl implements DietCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public DietResponse findByUser(User user) {
        QDiet diet = QDiet.diet;

        return queryFactory
                .select(new QDietResponse(
                        diet.calorie.sum(),
                        diet.carbo.sum(),
                        diet.protein.sum(),
                        diet.fat.sum(),
                        diet.sodium.sum(),
                        diet.sugar.sum(),
                        diet.transFat.sum(),
                        diet.saturatedFat.sum(),
                        diet.cholesterol.sum()
                ))
                .from(diet)
                .where(diet.user.eq(user))
                .groupBy(diet.user)
                .fetchOne();
    }
}
