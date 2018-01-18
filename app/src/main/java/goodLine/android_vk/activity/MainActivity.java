package goodLine.android_vk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import java.util.ArrayList;
import java.util.List;
import goodLine.android_vk.R;
import goodLine.android_vk.adapter.RecyclerViewAdapter;
import goodLine.android_vk.model.Person;

public class MainActivity extends AppCompatActivity {

    private VKApiUserFull user;
    private Person person,personselect;
    private String query;
    private SearchView searchView;
    private Intent intentDialog;
    private String text;
    private RecyclerView recyclerView;
    private Context context;
    private List<Person> persons;
    private List<Person> personFinded;
    public String s;
    private String[] scope=new String[]{VKScope.FRIENDS,VKScope.MESSAGES, VKScope.PHOTOS};
    private RecyclerTouchListener recyclerTouchListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;
        persons = new ArrayList<Person>();
        personFinded = new ArrayList<Person>();

        VKSdk.login(this, scope);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerTouchListener = new RecyclerTouchListener(context, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showProfile(position);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {

                VKRequest request = VKApi.friends().get(
                                VKParameters.from(VKApiConst.FIELDS, "first_name,last_name,photo_50, photo_400_orig,online,id,screen_name,sex,relation"));

                        request.executeWithListener(new VKRequest.VKRequestListener() {
                            @Override
                            public void onComplete(VKResponse response) {
                                super.onComplete(response);
                                VKList usersList = (VKList) response.parsedModel;


                                for (Object obj : usersList) {
                                    user = (VKApiUserFull) obj;
                                    persons.add(new Person(user.first_name, user.last_name,
                                            user.photo_50, user.photo_400_orig, user.sex,user.screen_name, user.relation,user.id));


                                }
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(persons, context);
                                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                recyclerView.addOnItemTouchListener(recyclerTouchListener);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setHasFixedSize(true);
                                searchView = (SearchView) findViewById(R.id.menu_search);
                                query=searchView.getQuery().toString();
                                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                     personFinded.clear();
                                        for (int i=0;i<persons.size();i++) {
                                            personselect = persons.get(i);
                                            s = personselect.getFirstName() + " " + personselect.getLastName();
                                            if (s.lastIndexOf(newText) > -1) {
                                                personFinded.add(personselect);
                                                RecyclerViewAdapter adapter=new RecyclerViewAdapter(personFinded,context);
                                                recyclerView.addOnItemTouchListener(recyclerTouchListener);
                                                recyclerView.setAdapter(adapter);
                                                recyclerView.setLayoutManager(layoutManager);
                                                recyclerView.setHasFixedSize(true);
                                                adapter.notifyDataSetChanged();
                                                Log.d("sravnenie", query + " совпадает с " + s);
                                            }
                                        }
                                           Log.d("sravnenieNOMER", "несовпадение");
                                                    return false;
                                        }
                                });

                    }
                });

            }

            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context,RecyclerView recyclerView,
                                     ClickListener clickListener){
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
            this.clickListener=clickListener;
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View view = rv.findChildViewUnder(e.getX(),e.getY());
            if(view!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(view,rv.getChildLayoutPosition(view));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public static interface ClickListener{
        public void onClick(View view,int position);

    }

    public void showProfile(int index) {

        boolean flagReady=false;
        intentDialog = new Intent(MainActivity.this, DialogActivity.class);
        person = personFinded.get(index);

        intentDialog.putExtra(getString(R.string.user_id), (person.getID()));
        intentDialog.putExtra(getString(R.string.first_last_name), (person.getFirstName() + person.getLastName()));
       intentDialog.putExtra(getString(R.string.avatarPhoto),person.getPhotoID400());//передача айди фотки
        intentDialog.putExtra("first_name_person",person.getFirstName());
        intentDialog.putExtra("last_name_person",person.getLastName());
        intentDialog.putExtra("sex",person.getSex());
        intentDialog.putExtra("relation",person.getRelation());


        Log.d("sex", String.valueOf(person.getSex()));

        if(person.isOnline()){
            intentDialog.putExtra(getString(R.string.last_seen) ,0);
            flagReady=true;


        }
        else {
            VKRequest request = VKApi.users().get(
                    VKParameters.from(VKApiConst.USER_ID,person.getID(),VKApiConst.FIELDS, "last_seen"));

            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);

                    VKList usersList = (VKList)response.parsedModel;
                    VKApiUserFull userOne= (VKApiUserFull) usersList.get(0);
                    intentDialog.putExtra(getString(R.string.last_seen), userOne.last_seen);
                    startActivity(intentDialog);
                }
            });
        }

       if(flagReady) startActivity(intentDialog);
    }

}