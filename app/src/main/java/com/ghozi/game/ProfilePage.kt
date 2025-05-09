package com.ghozi.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.ghozi.game.databinding.FragmentProfilePageBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ProfilePage : Fragment() {

    // Declare the binding variable
    private var _binding: FragmentProfilePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment and set up view binding
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Access the TextView through binding
        if (currentUser != null) {
            binding.profil.text = "${currentUser.email}"
        } else {
            binding.profil.text = "No user is logged in."
        }

        // Set up the OnClickListener for btnLogout
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Intent(requireContext(), LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(requireContext(), "Logout success!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUser.setOnClickListener {
            rep()
        }
        binding.btnHiragana.setOnClickListener {
            rep2()
        }
        binding.btnStage.setOnClickListener {
            rep3()
        }
        binding.btnQuestion.setOnClickListener {
            rep4()
        }

        return view
    }

    private fun rep() {
        try {
            // Get the PDF from assets
            val pdfFileName = "data_user.pdf"
            val inputStream: InputStream = requireContext().assets.open(pdfFileName)
            val file = File(requireContext().cacheDir, pdfFileName)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.close()
            inputStream.close()

            // Open the PDF using an Intent
            val pdfUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)

        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error opening PDF.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun rep2() {
        try {
            // Get the PDF from assets
            val pdfFileName = "data_hiragana.pdf"
            val inputStream: InputStream = requireContext().assets.open(pdfFileName)
            val file = File(requireContext().cacheDir, pdfFileName)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.close()
            inputStream.close()

            // Open the PDF using an Intent
            val pdfUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)

        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error opening PDF.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun rep3() {
        try {
            // Get the PDF from assets
            val pdfFileName = "data_stage.pdf"
            val inputStream: InputStream = requireContext().assets.open(pdfFileName)
            val file = File(requireContext().cacheDir, pdfFileName)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.close()
            inputStream.close()

            // Open the PDF using an Intent
            val pdfUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)

        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error opening PDF.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun rep4() {
        try {
            // Get the PDF from assets
            val pdfFileName = "data_pertanyaan.pdf"
            val inputStream: InputStream = requireContext().assets.open(pdfFileName)
            val file = File(requireContext().cacheDir, pdfFileName)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.close()
            inputStream.close()

            // Open the PDF using an Intent
            val pdfUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)

        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error opening PDF.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up binding reference
        _binding = null
    }
}
