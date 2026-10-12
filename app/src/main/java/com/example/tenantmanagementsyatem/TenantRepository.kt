package com.example.tenantmanagementsyatem

object TenantRepository {
    val tenants = mutableListOf<Tenant>()

    fun addTenant(tenant: Tenant) {
        tenants.add(tenant)
    }

    fun totalRent(): Double {
        return tenants.sumOf { tenant ->
            tenant.rent.toDoubleOrNull() ?: 0.0
        }
    }
}
