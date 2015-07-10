using AppStore.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using Windows.Data.Xml.Dom;
using Windows.Storage;

namespace AppStore.Common
{
    class AppReader
    {
        /*
        public static List<Apps> listApps()
        {
            List<AppInfo> mFileList = new List<AppInfo>();
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
            Resources res = context.getResources();
            AssetManager am = res.getAssets();
            string appList[], iconList[];
            try
            {
                appList = am.list("Apps");
                iconList = am.list("Icons");
                if (appList != null)
                {
                    for (int i = 0; i < appList.length; i++)
                    {
                        AppInfo app = new AppInfo();
                        app.Name = (appList[i].substring(0, appList[i].indexOf(".buildmlearn")));
                        if (!SP.contains(app.Name))
                        {
                            SharedPreferences.Editor editor1 = SP.edit();
                            editor1.putBoolean(app.Name, false);
                            editor1.commit(); continue;
                        }
                        if (!SP.getBoolean(app.Name, false)) continue;

                        app.AppIcon = BitmapFactory.decodeStream(am.open("Icons/" + iconList[i]));
                        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("Apps/" + appList[i])));
                        string type = br.readLine();
                        if (type.contains("InfoTemplate")) app.Type = 0;
                        else if (type.contains("QuizTemplate")) app.Type = 2;
                        else if (type.contains("FlashCardsTemplate")) app.Type = 1;
                        else if (type.contains("SpellingTemplate")) app.Type = 3;
                        br.readLine();
                        type = br.readLine();
                        int x = type.indexOf("<name>") + 6;
                        int y = type.indexOf("<", x + 1);
                        app.Author = type.substring(x, y);
                        mFileList.add(app);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            AppList = mFileList;
            return mFileList;
        }
        */
        public static void readInfoFile(string fileName)
        {
            try
            {
                InfoModel model = InfoModel.getInstance();
                List<string> infoTitleList = new List<string>();
                List<string> infoDescriptionList = new List<string>();
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(XDocument.Load("Assets/" + fileName + ".xml").ToString());
                model.setInfoName(doc.GetElementsByTagName("title").ElementAt(0).InnerText.Trim());
                model.setInfoDescription(doc.GetElementsByTagName("description").ElementAt(0).InnerText.Trim());
                string[] author = doc.GetElementsByTagName("author").ElementAt(0).InnerText.Split('\n');
                model.setInfoAuthor(author[1].Trim());
                model.setInfoAuthorEmail(author[2].Trim());
                model.setInfoVersion(doc.GetElementsByTagName("version").ElementAt(0).InnerText.Trim());
                XmlNodeList info_title = doc.GetElementsByTagName("item_title");
                XmlNodeList info_description = doc.GetElementsByTagName("item_description");
                for (int i = 0; i < info_title.Length; i++)
                {
                    infoTitleList.Add(info_title.ElementAt(i).InnerText.Trim());
                    infoDescriptionList.Add(info_description.ElementAt(i).InnerText.Trim());
                }
                model.setListTitleList(infoTitleList);
                model.setInfoDescriptionList(infoDescriptionList);
            }
            catch (Exception) { }
        }
        public static void readQuizFile(string fileName)
        {
            try
            {
                QuizModel model = QuizModel.getInstance();
                List<Question> mQuestionList = new List<Question>();
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(XDocument.Load("Assets/" + fileName + ".xml").ToString());
                model.setQuizName(doc.GetElementsByTagName("title").ElementAt(0).InnerText.Trim());
                model.setQuizDescription(doc.GetElementsByTagName("description").ElementAt(0).InnerText.Trim());
                string[] author = doc.GetElementsByTagName("author").ElementAt(0).InnerText.Split('\n');
                model.setQuizAuthor(author[1].Trim());
                model.setQuizAuthorEmail(author[2].Trim());
                model.setQuizVersion(doc.GetElementsByTagName("version").ElementAt(0).InnerText.Trim());
                XmlNodeList questions = doc.GetElementsByTagName("item");
                for (int i = 0; i < questions.Length; i++)
                {
                    Question q = new Question();
                    string[] ar = questions.ElementAt(i).InnerText.Split('\n');
                    List<string> options = new List<string>();
                    options.Add(ar[2].Trim());
                    options.Add(ar[3].Trim());
                    options.Add(ar[4].Trim());
                    options.Add(ar[5].Trim());
                    q.setOptionNumber(Convert.ToInt16(ar[6].Trim()));
                    q.setAnswerOption(options);
                    q.setQuestion(ar[1].Trim());
                    mQuestionList.Add(q);
                }
                model.setQueAnsList(mQuestionList);
            }
            catch (Exception) { }
        }
        public static void readSpellingsFile(string fileName)
        {
            try
            {
                SpellingsModel model = SpellingsModel.getInstance();
                List<WordModel> wordList = new List<WordModel>();
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(XDocument.Load("Assets/" + fileName + ".xml").ToString());
                model.setPuzzleName(doc.GetElementsByTagName("title").ElementAt(0).InnerText.Trim());
                model.setPuzzleDescription(doc.GetElementsByTagName("description").ElementAt(0).InnerText.Trim());
                string[] author = doc.GetElementsByTagName("author").ElementAt(0).InnerText.Split('\n');
                model.setPuzzleAuthor(author[1].Trim());
                model.setPuzzleAuthorEmail(author[2].Trim());
                model.setPuzzleVersion(doc.GetElementsByTagName("version").ElementAt(0).InnerText.Trim());
                XmlNodeList item = doc.GetElementsByTagName("item");
                // looping through all item nodes <app>
                for (int i = 0; i < item.Length; i++)
                {
                    string[] ar = item.ElementAt(i).InnerText.Split('\n');
                    WordModel word = new WordModel(ar[1].Trim(), ar[2].Trim());
                    wordList.Add(word);
                }
                model.setSpellingsList(wordList);
            }
            catch (Exception) { }
        }
        public static void readFlashFile(string fileName)
        {
            try
            {
                FlashModel model = FlashModel.getInstance();
                List<Card> cardList = new List<Card>();
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(XDocument.Load("Assets/Apps/" + fileName + ".xml").ToString());
                model.setFlashName(doc.GetElementsByTagName("title").ElementAt(0).InnerText.Trim());
                model.setFlashDescription(doc.GetElementsByTagName("description").ElementAt(0).InnerText.Trim());
                string[] author = doc.GetElementsByTagName("author").ElementAt(0).InnerText.Split('\n');
                model.setFlashAuthor(author[1].Trim());
                model.setFlashAuthorEmail(author[2].Trim());
                model.setFlashVersion(doc.GetElementsByTagName("version").ElementAt(0).InnerText.Trim());
                XmlNodeList item = doc.GetElementsByTagName("item");
                // looping through all item nodes <app>
                for (int i = 0; i < item.Length; i++)
                {
                    string[] ar = item.ElementAt(i).InnerText.Split('\n');
                    Card card = new Card(ar[1], ar[2], ar[3], ar[4]);
                    cardList.Add(card);
                }
                model.setCardList(cardList);
            }
            catch (Exception) { }
        }
    }
}
