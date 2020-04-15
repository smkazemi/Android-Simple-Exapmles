package fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.FragmentExample.R
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {

    private var listener: FragmentListener? = null
    private lateinit var viewLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        viewLayout = inflater.inflate(
            R.layout.fragment_first,
            container, false
        )

        // set text for textView embedded in fragment layout by extract from fragment arguments
        viewLayout.txt_first.text = arguments?.getString(getString(R.string.txtKey))

        return viewLayout
    }

    fun changeTxt(text: String) {
        viewLayout.txt_first.text = " "
        viewLayout.txt_first.text = text
    }

    interface FragmentListener {
        fun onFragmentStateChange(state: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            // cast host activity to FragmentListener in order get interface
            listener = context as FragmentListener
            listener?.onFragmentStateChange("On Attach")

        } catch (e: ClassCastException) {

            throw ClassCastException(
                "${context.toString()} not implemented FragmentListener interface"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener?.onFragmentStateChange("On Create")
    }

    override fun onDetach() {
        super.onDetach()
        listener?.onFragmentStateChange("On Detach")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        listener?.onFragmentStateChange("On Restore")


    }
}
