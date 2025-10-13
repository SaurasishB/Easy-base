/**
 * SPDX-FileCopyrightText: (c) 2025 EasyBase
 * SPDX-License-Identifier: LGPL-2.1-or-later
 */

package com.easybase.core.auth.service;

import com.easybase.core.auth.entity.ResourceAction;

import java.util.List;
import java.util.UUID;

/**
 * External-facing service interface for resource action operations.
 * Performs permission checks before delegating to ResourceActionLocalService.
 * Never performs persistence directly - always delegates to ResourceActionLocalService.
 *
 * @author Akhash R
 */
public interface ResourceActionService {

	/**
	 * Activate a resource action.
	 * Requires PERMISSION:UPDATE permission.
	 *
	 * @param actionId the action ID
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	void activateResourceAction(UUID actionId);

	/**
	 * Create a new resource action.
	 * Requires PERMISSION:CREATE permission.
	 *
	 * @param resourceType the resource type
	 * @param actionKey the action key
	 * @param actionName the action name
	 * @param bitValue the bit value
	 * @param description the description
	 * @return the created resource action
	 * @throws com.easybase.common.exception.ConflictException if action already exists
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	ResourceAction createResourceAction(
		String resourceType, String actionKey, String actionName, int bitValue,
		String description);

	/**
	 * Deactivate a resource action.
	 * Requires PERMISSION:UPDATE permission.
	 *
	 * @param actionId the action ID
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	void deactivateResourceAction(UUID actionId);

	/**
	 * Delete a resource action.
	 * Requires PERMISSION:DELETE permission.
	 * WARNING: This should be used with caution as it may break existing permissions.
	 *
	 * @param actionId the action ID
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	void deleteResourceAction(UUID actionId);

	/**
	 * Get all active resource actions for a resource type.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param resourceType the resource type
	 * @return list of active resource actions
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	List<ResourceAction> getActiveResourceActions(String resourceType);

	/**
	 * Get all active resource actions.
	 * Requires PERMISSION:LIST permission.
	 *
	 * @return list of all active resource actions
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	List<ResourceAction> getAllActiveResourceActions();

	/**
	 * Get a resource action by resource type and action key.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param resourceType the resource type
	 * @param actionKey the action key
	 * @return the resource action
	 * @throws com.easybase.common.exception.ResourceNotFoundException if not found
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	ResourceAction getResourceAction(String resourceType, String actionKey);

	/**
	 * Get a resource action by ID.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param actionId the action ID
	 * @return the resource action
	 * @throws com.easybase.common.exception.ResourceNotFoundException if not found
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	ResourceAction getResourceActionById(UUID actionId);

	/**
	 * Get all resource actions for a resource type.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param resourceType the resource type
	 * @return list of resource actions
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	List<ResourceAction> getResourceActions(String resourceType);

	/**
	 * Get all resource actions sorted by bit value.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param resourceType the resource type
	 * @return list of resource actions ordered by bit value
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	List<ResourceAction> getResourceActionsOrdered(String resourceType);

	/**
	 * Check if a resource action exists.
	 * Requires PERMISSION:VIEW permission.
	 *
	 * @param resourceType the resource type
	 * @param actionKey the action key
	 * @return true if the action exists
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	boolean resourceActionExists(String resourceType, String actionKey);

	/**
	 * Update a resource action.
	 * Requires PERMISSION:UPDATE permission.
	 *
	 * @param actionId the action ID
	 * @param actionName the new action name
	 * @param description the new description
	 * @param active the active status
	 * @return the updated resource action
	 * @throws com.easybase.common.exception.ResourceNotFoundException if not found
	 * @throws com.easybase.common.exception.ForbiddenException if permission denied
	 */
	ResourceAction updateResourceAction(
		UUID actionId, String actionName, String description, boolean active);

}