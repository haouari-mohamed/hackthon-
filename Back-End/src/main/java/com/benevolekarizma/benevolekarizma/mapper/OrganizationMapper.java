package com.benevolekarizma.benevolekarizma.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationRequest;
import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationResponse;
import com.benevolekarizma.benevolekarizma.models.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrganizationMapper {

    public Organization convertToEntity(OrganizationRequest organizationRequest) {
        return Organization.builder()
                .username(organizationRequest.getUsername())
                .firstName(organizationRequest.getFirstName())
                .lastName(organizationRequest.getLastName())
                .email(organizationRequest.getEmail())
                .phoneNumber(organizationRequest.getPhoneNumber())
                .password(organizationRequest.getPassword())
                .role(organizationRequest.getRole())
                .region(organizationRequest.getRegion())
                .orgName(organizationRequest.getOrgName())
                .orgPhoneNumber(organizationRequest.getOrgPhoneNumber())
                .orgAddress(organizationRequest.getOrgAddress())
                .build();
    }

    public OrganizationResponse convertToDTO(Organization organization) {
        return OrganizationResponse.builder()
                .username(organization.getUsername())
                .firstName(organization.getFirstName())
                .lastName(organization.getLastName())
                .email(organization.getEmail())
                .phoneNumber(organization.getPhoneNumber())
                .password(organization.getPassword())
                .role(organization.getRole())
                .region(organization.getRegion())
                .orgName(organization.getOrgName())
                .orgPhoneNumber(organization.getOrgPhoneNumber())
                .orgAddress(organization.getOrgAddress())
                .build();
    }

    public List<OrganizationResponse> convertToDTOList(List<Organization> organizations) {
        return organizations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
