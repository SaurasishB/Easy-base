/**
 * SPDX-FileCopyrightText: (c) 2025 EasyBase
 * SPDX-License-Identifier: LGPL-2.1-or-later
 */

package com.easybase.core.data.engine.service;

import com.easybase.core.data.engine.entity.Attribute;
import com.easybase.core.data.engine.entity.Collection;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Local service interface for collection business logic and data operations.
 * Contains all business logic, repository calls, and transaction management.
 * Does NOT perform permission checks - that's the responsibility of CollectionService.
 *
 * @author Akhash R
 */
public interface CollectionLocalService {

	/**
	 * Creates a new collection.
	 *
	 * @param tenantId the tenant ID
	 * @param collectionName the collection name
	 * @param attributes the collection attributes
	 * @return the created collection
	 * @throws com.easybase.common.exception.ConflictException if collection already exists
	 */
	Collection createCollection(
		UUID tenantId, String collectionName, List<Attribute> attributes);

	/**
	 * Deletes a collection.
	 *
	 * @param collectionId the collection ID
	 * @throws com.easybase.common.exception.ResourceNotFoundException if collection not found
	 */
	void deleteCollection(UUID collectionId);

	/**
	 * Gets a collection by ID.
	 *
	 * @param collectionId the collection ID
	 * @return the collection
	 * @throws com.easybase.common.exception.ResourceNotFoundException if not found
	 */
	Collection getCollection(UUID collectionId);

	/**
	 * Gets a collection by tenant and name.
	 *
	 * @param tenantId the tenant ID
	 * @param collectionName the collection name
	 * @return the collection
	 * @throws com.easybase.common.exception.ResourceNotFoundException if not found
	 */
	Collection getCollection(UUID tenantId, String collectionName);

	/**
	 * Gets collections for a tenant with pagination.
	 *
	 * @param tenantId the tenant ID
	 * @param pageable the pagination parameters
	 * @return page of collections
	 */
	Page<Collection> getCollections(UUID tenantId, Pageable pageable);

	/**
	 * Updates a collection's attributes.
	 *
	 * @param collectionId the collection ID
	 * @param newAttributes the new attributes
	 * @return the updated collection
	 * @throws com.easybase.common.exception.ResourceNotFoundException if collection not found
	 */
	Collection updateCollection(
		UUID collectionId, List<Attribute> newAttributes);

}