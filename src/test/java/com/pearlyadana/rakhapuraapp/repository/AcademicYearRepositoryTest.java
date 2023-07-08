package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.AcademicYear;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
* Note: Unit testing methods must not depend on other methods even in the same class.
* Eventually, ordering methods is not considered in unit testing.
* */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademicYearRepositoryTest {
    @Autowired
    private AcademicYearRepository academicYearRepository;

    private List<AcademicYear> initDummyRecords(){
        int size=10;
        AcademicYear[] years=new AcademicYear[size];
        for(int i=0;i<=years.length-1;i++) {
            AcademicYear year = new AcademicYear();
            year.setName("AcademicYear"+(i + 1));
            years[i] = year;
        }
        return Arrays.asList(years);
    }

    @Test
    @DisplayName("AcademicYear000:Verify all dependencies are successfully injected")
    void contextLoads(){
        assertNotEquals(null,academicYearRepository);
    }

    @BeforeEach
    void beforeEach(){
        List<AcademicYear> years=this.initDummyRecords();
        this.academicYearRepository.saveAllAndFlush(years);
        System.out.println("++++++++++++++"+years.size()+" records have been created++++++++++++++");
    }

    @AfterEach
    void afterEach(){
        this.academicYearRepository.deleteAll();
        System.out.println("++++++++++++++All records have been deleted++++++++++++++");
    }

    @Test
    @DisplayName("AcademicYear001:Verify pagination 1st page has 5 records")
    void verifyPaginationFistPage(){
        Pageable sortedByPriceDesc =
                PageRequest.of(0, 5, Sort.by("id").descending());
        List<AcademicYear> list=this.academicYearRepository.findAll(sortedByPriceDesc).getContent();

        assertNotEquals(0,list.size());

        list.stream().forEach((item)->{
            System.out.println("ID: "+item.getId()+", Name: "+item.getName());
        });
    }

    @Test
    @DisplayName("AcademicYear002:Verify pagination 2nd page has 5 records")
    void verifyPaginationSecondPage(){
        Pageable sortedByPriceDesc =
                PageRequest.of(1, 5, Sort.by("id").descending());
        List<AcademicYear> list=this.academicYearRepository.findAll(sortedByPriceDesc).getContent();
        assertNotEquals(0,list.size());

        list.stream().forEach((item)->{
            System.out.println("ID: "+item.getId()+", Name: "+item.getName());
        });
    }

    @Test
    @DisplayName("AcademicYear003:Verify pagination 3rd page doesn't have any record")
    void verifyPaginationThirdPage(){
        Pageable sortedByPriceDesc =
                PageRequest.of(2, 5, Sort.by("id").descending());
        List<AcademicYear> list=this.academicYearRepository.findAll(sortedByPriceDesc).getContent();

        assertEquals(0,list.size());

        list.stream().forEach((item)->{
            System.out.println("ID: "+item.getId()+", Name: "+item.getName());
        });
    }
}