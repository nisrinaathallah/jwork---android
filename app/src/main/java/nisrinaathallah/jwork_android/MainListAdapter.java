package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * inisiasi class
 */
public class MainListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Recruiter> listDataHeader;
    private HashMap<Recruiter, ArrayList<Job>> listDataChild;

    /**
     * constructor untuk Main List Adapter
     * @param context
     * @param listDataHeader
     * @param listDataChild
     */
    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader, HashMap<Recruiter, ArrayList<Job>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    /**
     * metgod getter untuk menghitung group
     * @return
     */
    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    /**
     * getter menghitung jumlah anak
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    /**
     * ethod untuk getter group pada Recruiter
     * @param groupPosition
     * @return
     */
    @Override
    public Recruiter getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    /**
     * method untuk getter child pada Job
     * @param groupPosition
     * @param childPosition
     * @return childPosition
     */
    @Override
    public Job getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    /**
     * method untuk getter group berdasarkan id
     * @param groupPosition
     * @return groupPosition
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * method untuk getter child berdasarkan id
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * method untuk memeriksa kestabilan id
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * method untuk memeriksa posisi group
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Recruiter recruiter = (Recruiter)  getGroup(groupPosition);
        String headerTitle = recruiter.getName();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_recruiter, null);
        }

        TextView lbListHeader = convertView.findViewById(R.id.lbListHeader);
        lbListHeader.setTypeface(null, Typeface.BOLD);
        lbListHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * method untuk menampilkan child
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Job job = (Job) getChild(groupPosition, childPosition);
        String childText = job.getName();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_job, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.lbListItem);
        txtListChild.setText(childText);

        return convertView;
    }

    /**
     * method untuk mengecek child yang dapat dipilih
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}