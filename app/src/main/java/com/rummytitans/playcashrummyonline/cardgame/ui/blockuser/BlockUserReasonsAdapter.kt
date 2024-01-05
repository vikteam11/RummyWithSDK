package com.rummytitans.playcashrummyonline.cardgame.ui.blockuser


import com.rummytitans.playcashrummyonline.cardgame.databinding.ItemBlockUserReasonBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BlockUserReasonsAdapter(var listResponse: MutableList<String>) :
    RecyclerView.Adapter<com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseViewHolder {
        context = parent.context
        val binding = ItemBlockUserReasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listResponse.size

    override fun onBindViewHolder(holder: com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    private inner class ViewHolder constructor(var mBinding: ItemBlockUserReasonBinding) :
        com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.reason = listResponse[position]

            mBinding.executePendingBindings()
        }
    }
}