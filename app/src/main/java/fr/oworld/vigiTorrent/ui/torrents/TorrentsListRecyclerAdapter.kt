package oworld.co.splitexpenses.UI.ListEvent

/**
 * Created by olivierbernal on 19/03/18 for KARTABLE
 */
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import fr.oworld.vigiTorrent.databinding.RowTorrentBinding
import fr.oworld.vigiTorrent.ui.torrents.TorrentsFragment

class TorrentsListRecyclerAdapter(var torrents : MutableList<String>) : RecyclerView.Adapter<TorrentsListRecyclerAdapter.TorrentHolder>()  {
    public var delegate: TorrentsFragment? = null
    public var delegateOnClick: TorrentsFragment.OnTorrentClickedListener? = null

    override fun onBindViewHolder(holder: TorrentHolder, position: Int) {
        holder.bind(torrents[position])
    }

    override fun getItemCount(): Int {
        return torrents.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TorrentHolder {
        val binding = RowTorrentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return TorrentHolder(binding, this)
    }

    class TorrentHolder(var binding: RowTorrentBinding, var adapter: TorrentsListRecyclerAdapter) : ViewHolder(binding.root), View.OnClickListener {
        private var torrent: String? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (adapter != null && torrent != null) {
                adapter.delegateOnClick?.onTorrentClicked(torrent!!)
            }
        }

        fun bind(torrent: String) {
            this.torrent = torrent
            binding.torrentName.text = torrent
        }
    }
}
