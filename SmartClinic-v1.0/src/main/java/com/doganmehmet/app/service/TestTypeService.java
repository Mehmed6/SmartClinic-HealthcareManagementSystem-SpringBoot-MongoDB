package com.doganmehmet.app.service;

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

    public TestTypeDTO save(TestTypeRequest request)
    {
        if (m_testTypeRepository.existsByName(request.getTestTypeName()))
            throw new ApiException(MyError.TEST_TYPE_ALREADY_EXISTS);

        var testType = m_testTypeMapper.toTestType(request);
        return m_testTypeMapper.toTestTypeDTO(m_testTypeRepository.save(testType));
    }

    public TestTypeDTO findTestTypeById(Long id)
    {
        var testType = m_testTypeRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));
        return m_testTypeMapper.toTestTypeDTO(testType);
    }

    public TestTypeDTO findTestTypeByName(String name)
    {
        var testType = m_testTypeRepository.findByName(name)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        return m_testTypeMapper.toTestTypeDTO(testType);
    }

    public List<TestTypeDTO> findAllTestTypes()
    {
        return m_testTypeMapper.toTestTypeDTOs(m_testTypeRepository.findAll());
    }

    public TestTypeDTO updateTestType(Long testTypeId, TestTypeRequest request)
    {
        var testType = m_testTypeRepository.findById(testTypeId)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        testType.setName(request.getTestTypeName());

        return m_testTypeMapper.toTestTypeDTO(m_testTypeRepository.save(testType));
    }

    @Transactional
    public void deleteTestType(Long testTypeId)
    {
        if (!m_testTypeRepository.existsById(testTypeId))
            throw new ApiException(MyError.TEST_TYPE_NOT_FOUND);

        m_testTypeRepository.deleteById(testTypeId);
    }

    @Transactional
    public void deleteTestTypeByName(String name)
    {
        var testType = m_testTypeRepository.findByName(name)
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND));

        m_testTypeRepository.delete(testType);
    }

    @Transactional
    public void deleteAllTestTypes()
    {
        m_testTypeRepository.deleteAll();
    }
}
