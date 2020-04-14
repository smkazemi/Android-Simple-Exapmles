package fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.FragmentExample.R
import kotlinx.android.synthetic.main.fragment_first.view.*
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {

    var listener: FragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(
            R.layout.fragment_first,
            container, false
        )

        // set text for textView embedded in fragment layout by a listener from activity
        viewLayout.txt_first.text = listener?.getText()


        return viewLayout
    }

    interface FragmentListener {
        fun getText(): String
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            // cast host activity to FragmentListener in order get interface
            listener = context as FragmentListener

        } catch (e: ClassCastException) {

            throw ClassCastException(
                "${context.toString()} not implemented FragmentListener interface"
            )
        }
    }

}
