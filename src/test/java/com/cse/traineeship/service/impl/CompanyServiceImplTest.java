package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock CompanyRepository companyRepository;
    @InjectMocks CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("Acme");
        company.setLocation("Athens");
    }

    @Test
    void findAll_delegatesToRepository() {
        when(companyRepository.findAll()).thenReturn(List.of(company));
        List<Company> result = companyService.findAll();
        assertEquals(1, result.size());
        assertSame(company, result.get(0));
        verify(companyRepository).findAll();
    }

    @Test
    void findById_existing_returnsCompany() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Company result = companyService.findById(1L);
        assertSame(company, result);
        verify(companyRepository).findById(1L);
    }

    @Test
    void findById_notFound_throws() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> companyService.findById(1L));
    }


}
