package com.example.tenantmanagementsyatem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tenantmanagementsyatem.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newTenant = args.newTenant
        if (newTenant != null) {
            binding.newTenantGroup.visibility = View.VISIBLE
            binding.newTenantTextView.text = newTenant.summary()

            binding.callButton.setOnClickListener {
                if (newTenant.phone.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${newTenant.phone}"))
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Save a tenant first", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            val lastSaved = TenantRepository.tenants.lastOrNull()
            if (lastSaved != null) {
                binding.newTenantGroup.visibility = View.VISIBLE
                binding.newTenantTextView.text = lastSaved.summary()
                binding.callButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${lastSaved.phone}"))
                    startActivity(intent)
                }
            } else {
                binding.newTenantGroup.visibility = View.GONE
                binding.callButton.setOnClickListener {
                    Toast.makeText(requireContext(), "Save a tenant first", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val allTenants = TenantRepository.tenants
        if (allTenants.isEmpty()) {
            binding.allTenantsTextView.text = "No tenants saved yet."
        } else {
            val summaryList = allTenants.joinToString("\n\n") { it.summary() }
            binding.allTenantsTextView.text = summaryList
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
