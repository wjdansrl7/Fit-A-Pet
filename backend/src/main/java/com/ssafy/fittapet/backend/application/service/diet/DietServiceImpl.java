package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import com.ssafy.fittapet.backend.domain.entity.Diet;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.diet.DietRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DietServiceImpl implements DietService{

    private final DietRepository dietRepository;

    @Override
    public Long createDietData(HealthDietRequest healthDietRequest, User user) {

//        Diet diet = Diet.builder().
//                user(user)
//                .healthDietRequest(healthDietRequest).
//                build();
//        if (dietRepository.findByUser(user) != null) {
//
//        }
        Diet diet = Diet.fromRequest(healthDietRequest, user);

        dietRepository.save(diet);
//        log.info(diet.getCalorie().toString());

        return diet.getId();
    }

    @Override
    public DietResponse getDailyDietData(User user) {
        DietResponse dietResponse = dietRepository.findByUser(user);
        return dietResponse;
    }

//    @Override
//    public Diet createDietCurrentTime(DietRequestDto dietRequestDto, User loginUser) {
//        // 현재 날짜에 해당하는 diet 정보 찾기
//        Diet currentDiet = dietRepository.findByUserAndCreatedAt(loginUser,
//                LocalDateTime.now());
//
//        // 생성된 활동 데이터가 없다면 생성
//        if (currentDiet == null) {
//            Diet diet = Diet.builder()
//                    .calorie(dietRequestDto.getCalorie())
//                    .sodium(Nutrient.builder().serving(dietRequestDto.getSodium().getServing())
//                            .ratio(dietRequestDto.getSodium().getRatio()).build())
//                    .carbo(Nutrient.builder().serving(dietRequestDto.getCarbo().getServing())
//                            .ratio(dietRequestDto.getCarbo().getRatio()).build())
//                    .totalSugars(Nutrient.builder().serving(dietRequestDto.getTotalSugars().getServing())
//                            .ratio(dietRequestDto.getTotalSugars().getRatio()).build())
//                    .fat(Nutrient.builder().serving(dietRequestDto.getFat().getServing())
//                            .ratio(dietRequestDto.getFat().getRatio()).build())
//                    .saturatedFattyAcid(Nutrient.builder().serving(dietRequestDto.getSaturatedFattyAcid().getServing())
//                            .ratio(dietRequestDto.getSaturatedFattyAcid().getRatio()).build())
//                    .transFattyAcid(Nutrient.builder().serving(dietRequestDto.getTransFattyAcid().getServing())
//                            .ratio(dietRequestDto.getTransFattyAcid().getRatio()).build())
//                    .cholesterol(Nutrient.builder().serving(dietRequestDto.getCholesterol().getServing())
//                            .ratio(dietRequestDto.getCholesterol().getRatio()).build())
//                    .protein(Nutrient.builder().serving(dietRequestDto.getProtein().getServing())
//                            .ratio(dietRequestDto.getProtein().getRatio()).build())
//                    .build();
//            return dietRepository.save(diet);
//        }
//
//        // 각 필드에 대해 serving 값을 누적 합 방식으로 업데이트
//        currentDiet.updateCalorie(currentDiet.getCalorie() + dietRequestDto.getCalorie()); // 칼로리는 누적 합산
//
//        currentDiet.updateSodium(accumulateNutrientServing(currentDiet.getSodium(),
//                dietRequestDto.getSodium()));
//        currentDiet.updateCarbo(accumulateNutrientServing(currentDiet.getCarbo(),
//                dietRequestDto.getCarbo()));
//        currentDiet.updateTotalSugars(accumulateNutrientServing(currentDiet.getTotalSugars(),
//                dietRequestDto.getTotalSugars()));
//        currentDiet.updateFat(accumulateNutrientServing(currentDiet.getFat(),
//                dietRequestDto.getFat()));
//        currentDiet.updateSaturatedFattyAcid(accumulateNutrientServing(currentDiet.getSaturatedFattyAcid(),
//                dietRequestDto.getSaturatedFattyAcid()));
//        currentDiet.updateTransFattyAcid(accumulateNutrientServing(currentDiet.getTransFattyAcid(),
//                dietRequestDto.getTransFattyAcid()));
//        currentDiet.updateCholesterol(accumulateNutrientServing(currentDiet.getCholesterol(),
//                dietRequestDto.getCholesterol()));
//        currentDiet.updateProtein(accumulateNutrientServing(currentDiet.getProtein(),
//                dietRequestDto.getProtein()));
//
//        return currentDiet;
//
//    }
//
//    // 기존 Nutrient 객체에 새로운 serving 값을 누적 합산하는 헬퍼 메서드
//    private Nutrient accumulateNutrientServing(Nutrient existingNutrient, NutrientDto nutrientDto) {
//        double newServing = existingNutrient.getServing() + nutrientDto.getServing(); // 기존 serving에 누적
//        // TODO: 비율 따로 계산 필요
//        return new Nutrient(newServing, existingNutrient.getRatio()); // ratio는 기존 값 유지
//    }
//
//    @Override
//    public DietResponseDto getDietCurrentTime(User loginUser) {
//        Diet currentDiet = dietRepository.findByUserAndCreatedAt(loginUser, LocalDateTime.now());
//
//        return DietResponseDto.builder()
//                .calorie(currentDiet.getCalorie())
//                .sodium(toNutrientDto(currentDiet.getSodium()))
//                .carbo(toNutrientDto(currentDiet.getCarbo()))
//                .totalSugars(toNutrientDto(currentDiet.getTotalSugars()))
//                .fat(toNutrientDto(currentDiet.getFat()))
//                .saturatedFattyAcid(toNutrientDto(currentDiet.getSaturatedFattyAcid()))
//                .transFattyAcid(toNutrientDto(currentDiet.getTransFattyAcid()))
//                .cholesterol(toNutrientDto(currentDiet.getCholesterol()))
//                .protein(toNutrientDto(currentDiet.getProtein()))
//                .build();
//    }
//
//    // Nutrient를 NutrientDto로 변환하는 헬퍼 메서드
//    private NutrientDto toNutrientDto(Nutrient nutrient) {
//        return new NutrientDto(nutrient.getServing(), nutrient.getRatio());
//    }
}
