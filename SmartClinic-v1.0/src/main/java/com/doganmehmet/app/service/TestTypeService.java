package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.TestTypeRequest;
import com.doganmehmet.app.dto.response.TestTypeDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.ITestTypeMapper;
import com.doganmehmet.app.repository.ITestTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestTypeService {
    private final ITestTypeRepository m_testTypeRepository;
    private final ITestTypeMapper m_testTypeMapper;

    @Auditable(
            action = "Create Test Type",
            entity = "TestType",
            description = "Test type created successfully"
    )
    public TestTypeDTO save(TestTypeRequest request)
    {
        if (m_testTypeRepository.existsByName(request.getTestTypeName()))
            throw new ApiException(MyError.TEST_TYPE_ALREADY_EXISTS);

        var testType = m_testTypeMapper.toTestType(request);
        return m_testTypeMapper.toTestTypeDTO(m_testTypeRepository.save(testType));
    }

    @Auditable(
            action = "Find Test Type by ID",
            entity = "TestType",
            description = "Test type found successfully"
    )
    public TestTypeDTO findTestTypeById(Long id)
    {
        var testType = m_testTypeRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));
        return m_testTypeMapper.toTestTypeDTO(testType);
    }

    @Auditable(
            action = "Find Test Type by Name",
            entity = "TestType",
            description = "Test type found successfully by name"
    )
    public TestTypeDTO findTestTypeByName(String name)
    {
        var testType = m_testTypeRepository.findByName(name)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        return m_testTypeMapper.toTestTypeDTO(testType);
    }

    @Auditable(
            action = "Find All Test Types",
            entity = "TestType",
            description = "All test types found successfully"
    )
    public List<TestTypeDTO> findAllTestTypes()
    {
        return m_testTypeMapper.toTestTypeDTOs(m_testTypeRepository.findAll());
    }

    @Auditable(
            action = "Update Test Type",
            entity = "TestType",
            description = "Test type updated successfully"
    )
    public TestTypeDTO updateTestType(Long testTypeId, TestTypeRequest request)
    {
        var testType = m_testTypeRepository.findById(testTypeId)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        testType.setName(request.getTestTypeName());

        return m_testTypeMapper.toTestTypeDTO(m_testTypeRepository.save(testType));
    }

    @Transactional
    @Auditable(
            action = "Delete Test Type by ID",
            entity = "TestType",
            description = "Test type deleted successfully by ID"
    )
    public void deleteTestType(Long testTypeId)
    {
        if (!m_testTypeRepository.existsById(testTypeId))
            throw new ApiException(MyError.TEST_TYPE_NOT_FOUND);

        m_testTypeRepository.deleteById(testTypeId);
    }

    @Transactional
    @Auditable(
            action = "Delete Test Type by Name",
            entity = "TestType",
            description = "Test type deleted successfully by name"
    )
    public void deleteTestTypeByName(String name)
    {
        var testType = m_testTypeRepository.findByName(name)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        m_testTypeRepository.delete(testType);
    }

    @Transactional
    @Auditable(
            action = "Delete All Test Types",
            entity = "TestType",
            description = "All test types deleted successfully"
    )
    public void deleteAllTestTypes()
    {
        m_testTypeRepository.deleteAll();
    }
}
