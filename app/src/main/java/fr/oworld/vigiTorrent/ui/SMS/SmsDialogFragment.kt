package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.Intent.getIntent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.ApiClient
import fr.oworld.vigiTorrent.databinding.FragmentDialogSmsBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SmsDialogFragment: DialogFragment() {

    private var _binding: FragmentDialogSmsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?, torrent: String?): SmsDialogFragment {
            val frag = SmsDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("torrent", torrent)
            frag.setArguments(args)
            return frag
        }
    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        val percentHeight = rect.height() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSmsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.okBtn.setOnClickListener {

            if (binding.mobileEditText.text.toString().startsWith("336") || binding.mobileEditText.text.toString().startsWith("337")) {
                subscribe(binding.nameEditText.text.toString(),
                        binding.mobileEditText.text.toString())
            } else {
                Toast.makeText(context, "Le numéro de téléphone doit être au format 33600000000 ou 337600000000", Toast.LENGTH_LONG)
                    .show()
            }

        }

        binding.nameTextLayout.hint = getString(R.string.nomTorrent)
        binding.mobileTextLayout.hint = getString(R.string.numeroTel)

        val value: String?
        if (arguments != null && arguments?.getString("torrent") != null ) {
            value = arguments?.getString("torrent")
            binding.nameEditText.setText(value!!.toString())
        }

        return root
    }

    fun subscribe(nomTorrent: String, mobile: String) {
        val paramObject = JSONObject()
        paramObject.put("nomTorrent", nomTorrent ?: "")
        paramObject.put("mobile", mobile ?: "")

        val call = ApiClient.apiService.postSubscription(paramObject.toString())

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), R.string.success_sub, Toast.LENGTH_LONG)
                        .show()

                    dialog?.dismiss()
                } else {
                    // Handle error
                    Toast.makeText(requireContext(), R.string.erreur_server, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Handle failure
                Toast.makeText(context, R.string.erreur_server, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        this.setWidthPercent(85)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}