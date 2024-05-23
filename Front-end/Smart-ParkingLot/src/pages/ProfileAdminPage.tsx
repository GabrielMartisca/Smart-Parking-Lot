import React from 'react';
import Navbar from '../components/Navbar';
import SearchBar from '../components/SearchBar';
import { useNavigate } from 'react-router-dom';

const ProfileAdminPage: React.FC = () => {
  const navigate = useNavigate();

  const handleDetailsClick = () => {
    navigate('/detailsadminpage');
  };

  const handleQuestionsClick = () => {
    navigate('/adminquestions');
  };

  const handleSeeAllUsersClick = () => {
    navigate('/seeallusersadmin');
  };

  /*const handleSeeAllParkingSpotsClick = () => {
    navigate('/seeallparkingspots');
  };*/

  /*const handleUnseenWarningsClick = () => {
    navigate('/unseenwarningadmin');
  };*/

  const handleLogout = () => {
    navigate('/');
  };

  return (
    <div className="flex flex-col min-h-screen bg-gray-100 dark:bg-gray-900 text-gray-900 dark:text-white">
      <div className="flex-grow p-4">
        <SearchBar />
        <div className="mt-4">
          <h1 className="text-2xl font-bold">Welcome, Admin!</h1>
        </div>
        <div className="mt-6 max-w-md mx-auto bg-gray-300 dark:bg-gray-800 p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-4">Admin Actions</h2>
          <div className="space-y-4">
            <button
              onClick={handleDetailsClick}
              className="w-full px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-900 dark:text-white font-bold rounded-lg shadow-md hover:bg-gray-400 dark:hover:bg-gray-600 transition duration-300"
            >
              Details
            </button>
            <button
              onClick={handleQuestionsClick}
              className="w-full px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-900 dark:text-white font-bold rounded-lg shadow-md hover:bg-gray-400 dark:hover:bg-gray-600 transition duration-300"
            >
              Questions
            </button>
            <button
              onClick={handleSeeAllUsersClick}
              className="w-full px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-900 dark:text-white font-bold rounded-lg shadow-md hover:bg-gray-400 dark:hover:bg-gray-600 transition duration-300"
            >
              See all users
            </button>
            <button
              onClick={handleLogout}
              className="w-full px-4 py-2 bg-purple-600 text-white font-bold rounded-lg shadow-md hover:bg-purple-700 transition duration-300"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
      <Navbar />
    </div>
  );
};

export default ProfileAdminPage;
